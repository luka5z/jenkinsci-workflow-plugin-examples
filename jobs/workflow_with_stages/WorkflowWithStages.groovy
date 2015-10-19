stage 'development'
node {
  echo 'Development stage.'
// ...
}

stage 'testing' 
node {
  echo 'Testing stage.'
// ...
}

// When pending, newest builds are always given priority when entering 
// such a throttled stage; older build - waiting for running stage to free 
// resources - will simply exit early if they preempted.
stage name: 'deployment', concurrency: 1
node {
  echo 'Deployment stage.'
// ...
  sleep 30
}
