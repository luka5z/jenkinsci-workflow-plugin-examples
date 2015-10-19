/**
 * Since workflows must survive Jenkins restarts, the state of the running 
 * program is periodically saved to disk so it can be resumed later (saves 
 * occur after every step or in the middle of steps such as sh).
 *
 * The “state” includes the whole control flow including: local variables, 
 * positions in loops, and   * so on. As such: any variable values used 
 * in your program should be numbers, strings, or other serializable types,
 * not “live” objects such as network connections.
 *
 * If you must use a nonserializable value temporarily: discard it before doing
 * anything else. When you keep the matcher only as a local variable inside a
 * function, it is automatically discarded as soon as the function returned.
 *
 * You can also explicitly discard a reference when you are done with it, by
 * assigning a null value.
 */
node('swarm') {
  git url: 'https://github.com/jglick/simple-maven-project-with-tests.git'
  def v = version(readFile('pom.xml'))
  if (v) {
    echo "Building version ${v}"
  }
  def mvnHome = tool 'M3'
  sh "${mvnHome}/bin/mvn -B -Dmaven.test.failure.ignore verify"
  step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])
  step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
}

/**
 * However the safest approach is to isolate use of nonserializable state 
 * inside a method marked with the annotation @NonCPS. Such a method
 * will be treated as “native” by the Workflow engine, and its local variables
 * never saved. However it may not make any calls to Workflow steps, ie. readFile.
 * 
 * CPS - Continuation-passing style
 */
@NonCPS
def version(text) {
  def matcher = text =~ '<version>(.+)</version>'
  matcher ? matcher[0][1] : null
}
