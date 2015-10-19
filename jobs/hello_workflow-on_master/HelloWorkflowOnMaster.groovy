// In this example, echo is a step: a function defined in a Jenkins plugin and made available to all workflows. 

// Execute it on specific node with label: master.
node('master') {
  echo 'hello from Workflow'
}
