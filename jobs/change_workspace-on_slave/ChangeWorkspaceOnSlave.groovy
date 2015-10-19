// Explicitly switch to another workspace location on current slave, without
// grabbing a nw executor slot. 
node('swarm') {
  ws ('/tmp') {
    echo 'hello from Workflow'
  }
}
