# scala-sudoku-solver

Simple 9x9 Sudoku solver in Scala

Build:

```bash
scalac sudoku.scala
```

Run:

```bash
scala SudokuSolver data/evil8104462993.txt
```

Where input is of the form:
```
--62-4-9-
4517-----
----8----
93-------
-2-6-7-4-
-------27
----4----
-----3674
-6-9-28--

```

## TODO

- Add unit tests
- Fix unnecessary copies
- Refactor to code to be more idiomatic for Scala