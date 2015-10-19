/**
 * When you run this script:
 * -  it should check out a Git repository and run Maven to build it. 
 * -  it will run some tests that might (at random) pass, fail, or be skipped. 
 *      If they fail, the mvn command will fail and your flow run will end with error.
 */
node {
// Instead of the simplest git step... 
//  git url: 'https://github.com/jglick/simple-maven-project-with-tests.git', branch: 'master'
// ...you can use the more general checkout step and specify any complex 
// configuration supported by the Git plugin.
// Pay attention that in some cases, part of a step configuration will force an object to 
// be of a fixed class. Thus, $class can be omitted entirely.
// In below example $class: 'BranchSpec' can be omitted.
  checkout scm: [$class: 'GitSCM', branches: [[$class: 'BranchSpec', name: '*/master']], userRemoteConfigs: [[url: 'https://github.com/jglick/simple-maven-project-with-tests.git']]]

  def mvnHome = tool 'M3'
// Use mvn as default, by adding it to executable path.
// Setting a variable such as PATH in this way is only safe if you are using a single slave
// for this build.
  env.PATH = "${mvnHome}/bin:${env.PATH}"
  sh "mvn -B -Dmaven.test.failure.ignore verify"
}
