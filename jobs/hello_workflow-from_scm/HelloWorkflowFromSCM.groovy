/**
 * The loaded file can contain statements at top level, which are run immediately. That
 * is fine if you want to use a single executor and workspace, and do not hard-coding the
 * slave label in the Jenkins job. 
 *
 * For complex cases, you should leave the external script in full control of slave allocation:
 * in such case case job can just load and run a closure (block of code to be run later) with 
 * load step.
 * The return value of the load step also becomes the return value of the closure which we run 
 * with the parentheses ().
 */
{ ->
// In this example, echo is a step: a function defined in a Jenkins plugin and made available to all workflows. 
  node('swarm') {
    hello 'Workflow'
  }
}

def hello(whom) {
  echo "hello from ${whom}"	
} 

