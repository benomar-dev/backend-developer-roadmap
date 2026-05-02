# Git Cheat Sheet

## Setup
```bash
git config --global user.name "Name"
git config --global user.email "email@example.com"
git config --global init.defaultBranch main
git config --list
```

## Create / Clone
```bash
git init                        # new local repo
git clone <url>                 # clone remote repo
git clone <url> <folder>        # clone into specific folder
```

## Stage & Snapshot
```bash
git status                      # show changed files
git add <file>                  # stage a specific file
git add .                       # stage all changes
git add -p                      # stage interactively (hunks)
git commit -m "message"         # commit staged changes
git commit -am "message"        # stage tracked + commit
git commit --amend -m "msg"     # edit last commit message
```

## Browse History
```bash
git log                         # full history
git log --oneline               # compact one-liner
git log --oneline --graph --all # visual branch graph
git diff                        # unstaged changes
git diff --staged               # staged vs last commit
git show <sha>                  # show a commit's changes
git blame <file>                # who changed each line
```

## Undo
```bash
git restore <file>              # discard unstaged changes
git restore --staged <file>     # unstage a file
git reset --soft HEAD~1         # undo commit (keep staged)
git reset --mixed HEAD~1        # undo commit (keep unstaged)
git reset --hard HEAD~1         # undo commit (discard all) ⚠️
git revert <sha>                # safe undo via new commit
git clean -fd                   # remove untracked files/dirs ⚠️
```

## Stash
```bash
git stash                       # stash working changes
git stash push -m "msg"         # stash with label
git stash list                  # list stashes
git stash apply                 # apply latest stash
git stash apply stash@{n}       # apply specific stash
git stash pop                   # apply + remove from list
git stash drop stash@{n}        # delete a stash
git stash clear                 # delete all stashes
```

## Branches
```bash
git branch                      # list local branches
git branch -a                   # list all (incl. remote)
git branch <name>               # create branch
git switch <name>               # switch branch (2.23+)
git switch -c <name>            # create + switch
git checkout -b <name>          # create + switch (old)
git branch -d <name>            # delete merged branch
git branch -D <name>            # force delete branch
git branch -m <old> <new>       # rename branch
```

## Merge & Rebase
```bash
git merge <branch>              # merge branch into current
git merge --no-ff <branch>      # always create merge commit
git merge --abort               # cancel in-progress merge
git rebase main                 # rebase current onto main
git rebase -i HEAD~3            # interactive rebase (last 3)
git rebase --abort              # cancel in-progress rebase
```

## Remote
```bash
git remote -v                   # list remotes
git remote add origin <url>     # add remote
git remote set-url origin <url> # change remote URL
git fetch origin                # download (don't merge)
git pull                        # fetch + merge
git pull --rebase               # fetch + rebase
git push -u origin <branch>     # push + set upstream
git push                        # push to tracked remote
git push origin --delete <br>   # delete remote branch
```

## Tags
```bash
git tag                         # list tags
git tag v1.0.0                  # lightweight tag
git tag -a v1.0.0 -m "msg"      # annotated tag
git push origin v1.0.0          # push one tag
git push origin --tags          # push all tags
git tag -d v1.0.0               # delete local tag
git push origin --delete v1.0.0 # delete remote tag
```

## Cherry-Pick & Bisect
```bash
git cherry-pick <sha>           # apply a commit to current branch
git bisect start                # start bisect session
git bisect good <sha>           # mark known-good commit
git bisect bad                  # mark current as bad
git bisect reset                # end bisect session
```

## Useful Shortcuts
```bash
git shortlog -sn                # commit counts by author
git log --stat                  # files changed per commit
git log --follow <file>         # follow renames
git diff HEAD~3 HEAD            # diff between commits
git stash branch <name>         # create branch from stash
```

## Conventional Commit Types
| Type | Meaning |
|------|---------|
| `feat` | New feature |
| `fix` | Bug fix |
| `docs` | Documentation |
| `style` | Formatting only |
| `refactor` | Code restructure |
| `test` | Tests |
| `chore` | Build/dependencies |
| `perf` | Performance |
| `ci` | CI/CD |

## Branch Naming
```
feature/<short-description>
bugfix/<issue-or-description>
hotfix/<urgent-fix>
release/<version>
```
