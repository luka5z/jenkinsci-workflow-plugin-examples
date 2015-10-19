// Explicitly switch to another workspace location on current slave, without
// grabbing a nw executor slot. 
node('swarm') {
  dir ('/tmp') {
    echo 'hello from Workflow'
  }
}
