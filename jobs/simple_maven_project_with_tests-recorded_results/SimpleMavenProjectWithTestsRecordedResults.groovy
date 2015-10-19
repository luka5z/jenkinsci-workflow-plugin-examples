/**
 * When you run this script:
 * -  it should check out a Git repository and run Maven to build it. 
 * -  it will run some tests that might (at random) pass, fail, or be skipped. 
 *      If they fail, the mvn command will fail and your flow run will end with error.
 */
node {
  git url: 'https://github.com/jglick/simple-maven-project-with-tests.git', branch: 'master'
  def mvnHome = tool 'M3'
// Use mvn as default, by adding it to executable path.
// Setting a variable such as PATH in this way is only safe if you are using a single slave
// for this build.
  env.PATH = "${mvnHome}/bin:${env.PATH}"
  sh "mvn -B -Dmaven.test.failure.ignore verify"

// Run step already defined in Jenkins for use in traditional projects.
// $class is used to pick the kind of object to create. It may be fully-qualified class name,
// but the simple name may be used when unambiguous. 
  step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])
// Create a delegate build step which could be use later.
  step new hudson.tasks.junit.JUnitResultArchiver('**/target/surefire-reports/TEST-*.xml')
}
