# Ratpack Infinity
Demonstrates pattern that creates memory leak.

## Steps
* Run the `./run.sh` # This builds project as shadow JAR and launches
* At anytime find pid and run `./heap.sh <pid>` to generate a heap dump.
* Connecting via VisualVM is also useful to show heap fill.
