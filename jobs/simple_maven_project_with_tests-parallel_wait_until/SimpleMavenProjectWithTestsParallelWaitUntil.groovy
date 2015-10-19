/**
 * Workflows can use a parallel step to perform multiple actions at once. 
 * This special step takes a map as its argument; 
 * keys are “branch names” (labels for your own benefit), and values are blocks to run.
 */
node('master') {
  echo 'Pre-parallel execution step.'
  git url: 'https://github.com/jenkinsci/parallel-test-executor-plugin-sample.git'
  archive 'pom.xml, src/'
}
/**
 * Parallel Test Executor plug-in let's you split apart your test runs.
 * IMPORTANT! 
 * When you run this flow for the first time, it will check out a project and 
 * run all of its tests in sequence.
 * The second and subsequent times you run it, the splitTests task will 
 * partition your tests into two sets of roughly equal runtime. The rest of 
 * the flow then runs these in parallel.
 */
def completed = 0
def splits = splitTests([$class: 'CountDrivenParallelism', size: 2])
def branches = [:]
branches["failFast"] = false
for (int i = 0; i < splits.size(); i++) {
  def exclusions = splits.get(i);
  branches["split${i}"] = {
    node('swarm') {
      echo "Parallel execution step - split${i}."
      sh 'rm -rf *'
// This prevent from happening situation when anyone pushes a nw Git 
// commit at the wrong time, which could lead to testing different sources in
// some branches.
      unarchive mapping: ['pom.xml' : '.', 'src/' : '.']
      writeFile file: 'exclusions.txt', text: exclusions.join("\n")
      sh "${tool 'M3'}/bin/mvn -B -Dmaven.test.failure.ignore test"
// The test results recorded in the build are cumulative.
      step([$class: 'JUnitResultArchiver', testResults: 'target/surefire-reports/*.xml'])
      ++completed
    }
  }
}
branches["waitUntil"] = {
  node('master') {
    waitUntil {
      echo "Waiting until all split execution steps complete..."
      if (completed == splits.size()) {
        return true
      }
      return false
    }
  }
}
/**
 * There are several new ideas at work here:
 *
 *  - A single flow build allocates several executors, potentially on different slaves,
 * at the same time. You can see these starting and finishing in the Jenkins
 * executor widget on the main screen.
 * - Each call to node gets its own workspace.
 */
parallel branches
node('master') {
  echo 'Post-parallel execution step'
}

