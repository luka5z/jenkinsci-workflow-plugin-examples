echo "Pre-parallel step"

parallel first: {
  // do something
  echo "First parallel step"
}, second: {
  // do something else
  echo "Second parallel step"
}, 
failFast: false

echo "Post-parallel step"
