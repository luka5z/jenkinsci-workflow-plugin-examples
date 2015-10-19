# jenkinsci-workflow-plugin-examples
Examples for Jenkins Workflow plugin


Remarks:
---
- Running Workflow scripts from SCM seems to enable Groovy sandbox and by that 
  scripts require permission before they could be successfully executed.

- Although `parallel` step finishes execution when all branches have been run, but
  that does not mean that it will keep those branches running until last one 
  finishes nor it will not start them all at the same time. 
  Each branches for `parallel` step starts as soon as resources are available.  
