node('swarm') {
  git url: 'https://github.com/jglick/simple-maven-project-with-tests.git'
  def v = version()
  if (v) {
    echo "Building version ${v}"
  }
  def mvnHome = tool 'M3'
  sh "${mvnHome}/bin/mvn -B -Dmaven.test.failure.ignore verify"
  step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])
  step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
}
def version() {
// readFile/writeFile steps loads/save text file from/to workspace location.
  def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
  matcher ? matcher[0][1] : null
}
// Unless your Script Security plugin is version 1.11 or higher, you may see a
// RejectedAccessException error at this point. If so, a Jenkins administrator will
// need to navigate to Manage Jenkins » In-process Script Approval and Approve
// staticMethod org.codehaus.groovy.runtime.ScriptBytecodeAdapter findRegex
// java.lang.Object java.lang.Object. Then try running your script again and it should work.
