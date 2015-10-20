# jenkinsci-workflow-plugin-examples
Examples for Jenkins Workflow plugin, based on official [tutorial](https://github.com/jenkinsci/workflow-plugin/blob/master/TUTORIAL.md). 

## Remarks
- Running Workflow scripts from SCM seems to enable Groovy sandbox and by that 
  scripts require permission before they could be successfully executed.

- Although `parallel` step finishes execution when all branches have been run, but
  that does not mean that it will keep those branches running until last one 
  finishes nor it will not start them all at the same time. 
  Each branches for `parallel` step starts as soon as resources are available.  

- To install experimental releases, like **Workflow Multibranch** feature, 
  **Update Center** needs to switch to `http://updates.jenkins-ci.org/experimental/update-center.json`.
  After all needed plugins have been successfully installed, you can revert to 
  official channel by switching back to `http://updates.jenkins-ci.org/update-center.json` update center.

  Based on Experimental Plugins Update Center [article](http://jenkins-ci.org/content/experimental-plugins-update-center).

## Resources
- [Workflow Plugin](https://github.com/jenkinsci/workflow-plugin)
- [Workflow Global Library](https://github.com/jenkinsci/workflow-plugin/blob/master/cps-global-lib/README.md)
- [CloudBees Docker Workflow](https://github.com/jenkinsci/docker-workflow-plugin)


