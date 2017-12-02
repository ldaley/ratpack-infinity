# Ratpack Infinity
Demonstrates pattern that creates memory leak.

## How to Run
* To infinitely iterate using Ratpack's Promise recursion causing heap exhaustion:
```
./run.sh
```

* To infinitely iterate by forking executions:
```
./run.sh -DinfiniteFork=true
```

* Generate a heap dump at any time using:
```
./heap.sh <pid>
```

# Findings
Ran test on both Ratpack 1.4.5 and 1.5.1.  Problem appears in both versions of Ratpack, 1.5.1
appears to go a little longer before exhausting the heap.

## Infinite Forked Executions 
Heap memory is properly cleared on each iteration.
[Download Heap Dump](https://drive.google.com/file/d/1TlJJ4VPUr0vDD1vUFce8IrlAdy_q_AcY/view?usp=sharing)
![Infinite Fork Heap](/reports/ratpack-infinity-fork-heap.png?raw=true "Infinite Fork Heap")
![Infinite Recursion Leak Suspect](/reports/ratpack-infinity-fork-leak-suspect.png?raw=true "Infinite Fork Leak Suspect")

## Infinite Recursive Promises Executions
Heap memory is leaked on each iteration.  The expectation here is that the internal trampoline
of Ratpack's Promise should behave much like an iterable with the objects created and scoped within
the iteration becoming eligible for GC at the end of each iteration.
[Download Heap Dump](https://drive.google.com/file/d/1o39GYftb5R71hoEhfdDkwDilxOjyj_Qj/view?usp=sharing) 
![Infinite Recursion Heap](/reports/ratpack-infinity-recursion-heap.png?raw=true "Infinite Recursion Heap")
![Infinite Recursion Leak Suspect](/reports/ratpack-infinity-recursion-leak-suspect.png?raw=true "Infinite Recursion Leak Suspect")
![Infinite Recursion Dominator Tree](/reports/ratpack-infinity-recursion-dominator-tree.png?raw=true "Infinite Recursion Dominator Tree")
