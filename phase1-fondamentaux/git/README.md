# Git & GitHub — Complete Guide

## 📖 Introduction

Git is a **distributed version control system** that tracks changes in your source code.
GitHub is a cloud hosting platform for Git repositories and collaboration.

---

## 1. Installation & Configuration

### Install Git

```bash
# Ubuntu / Debian
sudo apt update && sudo apt install git -y

# macOS (with Homebrew)
brew install git

# Verify
git --version
```

### Configure Your Identity

```bash
# Set your name and email (used in every commit)
git config --global user.name "Your Name"
git config --global user.email "you@example.com"

# Set default branch name to 'main'
git config --global init.defaultBranch main

# Set preferred editor (e.g., VS Code)
git config --global core.editor "code --wait"

# Show all configuration
git config --list
```

---

## 2. Core Concepts

| Concept | Description |
|---------|-------------|
| **Repository (repo)** | Folder tracked by Git; contains `.git/` directory |
| **Working tree** | Your actual files on disk |
| **Staging area (index)** | Buffer between working tree and repository |
| **Commit** | Snapshot of staged changes with a message |
| **Branch** | Independent line of development |
| **Remote** | A repository hosted elsewhere (GitHub, GitLab…) |
| **HEAD** | Pointer to the current commit / branch |

---

## 3. Basic Commands

### Starting a Repository

```bash
# Start tracking a new project
git init

# Clone an existing repository
git clone https://github.com/user/repo.git
git clone https://github.com/user/repo.git my-folder   # clone into custom folder
```

### Staging and Committing

```bash
# Check which files changed
git status

# Stage a specific file
git add filename.java

# Stage all changes
git add .

# Stage interactively (choose individual chunks)
git add -p

# Commit with an inline message
git commit -m "feat: add user authentication"

# Stage all tracked files AND commit in one step
git commit -am "fix: correct null pointer in UserService"

# Amend the last commit (message or staged content)
git commit --amend -m "feat: add user authentication (improved)"
```

### Viewing History

```bash
# Full history
git log

# Compact one-line-per-commit view
git log --oneline

# Graph view (branches visualised)
git log --oneline --graph --all

# Show changes introduced by a commit
git show <commit-sha>

# Show who modified each line of a file
git blame filename.java
```

### Undoing Changes

```bash
# Discard unstaged changes to a file
git restore filename.java            # Git 2.23+
git checkout -- filename.java        # older syntax

# Unstage a file (keep working tree changes)
git restore --staged filename.java
git reset HEAD filename.java         # older syntax

# Undo the last commit (keep changes staged)
git reset --soft HEAD~1

# Undo the last commit (keep changes unstaged)
git reset --mixed HEAD~1

# Undo the last commit AND discard changes (DESTRUCTIVE)
git reset --hard HEAD~1

# Create a new commit that reverts a previous commit (safe for shared history)
git revert <commit-sha>
```

---

## 4. Branching & Merging

### Branches

```bash
# List local branches
git branch

# List all branches (including remote-tracking)
git branch -a

# Create a new branch
git branch feature/login

# Switch to a branch
git switch feature/login             # Git 2.23+
git checkout feature/login           # older syntax

# Create AND switch in one command
git switch -c feature/login
git checkout -b feature/login        # older syntax

# Delete a merged branch (safe)
git branch -d feature/login

# Delete a branch regardless (force)
git branch -D feature/login
```

### Merging

```bash
# Merge feature branch into main
git switch main
git merge feature/login

# Merge without fast-forward (always creates a merge commit)
git merge --no-ff feature/login

# Abort a merge with conflicts
git merge --abort
```

### Resolving Conflicts

When a merge conflict occurs, Git marks the conflicting sections:

```
<<<<<<< HEAD
    // your version (main)
=======
    // incoming version (feature/login)
>>>>>>> feature/login
```

Steps to resolve:
1. Open the conflicting file(s) in your editor.
2. Remove the conflict markers and keep the correct code.
3. `git add <resolved-file>`
4. `git commit`

---

## 5. Remote Repositories

```bash
# List remote connections
git remote -v

# Add a remote
git remote add origin https://github.com/user/repo.git

# Push local branch to remote (and set upstream)
git push -u origin main

# Push subsequent commits
git push

# Push a feature branch
git push origin feature/login

# Fetch remote changes (doesn't merge)
git fetch origin

# Pull = fetch + merge
git pull origin main

# Pull with rebase (cleaner history)
git pull --rebase origin main
```

---

## 6. Git Flow — Branching Strategy

Git Flow defines a structured branching model:

```
main           ──●──────────────────────────●──  (production-ready)
                  │                          │
develop        ───●──●──●──●──●──────────────●──  (integration)
                        │       │
feature/*      ─────────●──●──●─┘            (individual features)
                                 │
release/*                       ●──●──●      (release preparation)
                                          │
hotfix/*                                  ●  (urgent production fixes)
```

### Branch Naming Conventions

| Prefix | Purpose | Example |
|--------|---------|---------|
| `main` / `master` | Production code | `main` |
| `develop` | Integration branch | `develop` |
| `feature/` | New feature | `feature/user-authentication` |
| `bugfix/` | Bug fix | `bugfix/fix-login-redirect` |
| `hotfix/` | Urgent production fix | `hotfix/security-patch-v1.2.1` |
| `release/` | Release preparation | `release/v1.3.0` |

---

## 7. Pull Requests & Code Reviews

### Creating a Pull Request

1. Push your feature branch: `git push origin feature/my-feature`
2. Open GitHub → your repo → **Compare & pull request**
3. Fill in the title (e.g., `feat: add user authentication`) and description
4. Assign reviewers
5. Link to related issues using `Closes #42`

### Good PR Practices

- Keep PRs small and focused (one concern per PR)
- Write a clear description: *what* changed and *why*
- Include screenshots for UI changes
- Respond to review comments promptly
- Squash commits when merging if history is messy

---

## 8. Stashing

```bash
# Save current changes temporarily (without committing)
git stash

# Stash with a descriptive message
git stash push -m "WIP: half-done login refactor"

# List all stashes
git stash list

# Apply the most recent stash (keeps it in the list)
git stash apply

# Apply a specific stash
git stash apply stash@{2}

# Apply AND remove from the list
git stash pop

# Drop a stash without applying
git stash drop stash@{0}

# Clear all stashes
git stash clear
```

---

## 9. Tagging

```bash
# List tags
git tag

# Create a lightweight tag
git tag v1.0.0

# Create an annotated tag (recommended for releases)
git tag -a v1.0.0 -m "Release version 1.0.0"

# Tag a specific commit
git tag -a v0.9.0 <commit-sha> -m "Beta release"

# Push a tag to remote
git push origin v1.0.0

# Push all tags
git push origin --tags

# Delete a local tag
git tag -d v1.0.0

# Delete a remote tag
git push origin --delete v1.0.0
```

---

## 10. Git Hooks

Hooks are scripts that run automatically at specific Git events.
They live in `.git/hooks/` (sample files end in `.sample`).

### Common Hooks

| Hook | When it fires | Common use |
|------|--------------|------------|
| `pre-commit` | Before creating a commit | Run linters, tests |
| `commit-msg` | After commit message is entered | Validate message format |
| `pre-push` | Before pushing to remote | Run full test suite |
| `post-merge` | After a successful merge | Install new dependencies |

### Example: `pre-commit` hook (enforce Conventional Commits format)

```bash
#!/bin/sh
# .git/hooks/pre-commit
# Run Maven tests before allowing a commit

mvn test -q
if [ $? -ne 0 ]; then
    echo "❌ Tests failed. Commit aborted."
    exit 1
fi
echo "✅ Tests passed."
```

Make it executable: `chmod +x .git/hooks/pre-commit`

---

## 11. Useful `.gitignore` Templates

```gitignore
# Java
*.class
*.jar
*.war
*.ear
target/
build/

# IntelliJ IDEA
.idea/
*.iml

# VS Code
.vscode/

# macOS
.DS_Store

# Spring Boot
application-local.properties
application-local.yml

# Environment files
.env
*.env.local
```

---

## 12. Conventional Commits

Format: `<type>(<scope>): <short summary>`

| Type | Meaning |
|------|---------|
| `feat` | New feature |
| `fix` | Bug fix |
| `docs` | Documentation only |
| `style` | Formatting (no logic change) |
| `refactor` | Code restructure without behaviour change |
| `test` | Add or fix tests |
| `chore` | Build process or dependency updates |
| `perf` | Performance improvement |
| `ci` | CI/CD configuration |

Examples:
```
feat(auth): add JWT token refresh endpoint
fix(user): correct null pointer when profile is missing
docs(readme): update installation instructions
test(cart): add unit tests for discount calculation
chore: upgrade Spring Boot to 3.2.0
```

---

## 📚 Resources

- [Pro Git Book (free)](https://git-scm.com/book/en/v2)
- [Learn Git Branching (interactive)](https://learngitbranching.js.org/)
- [GitHub Skills](https://skills.github.com/)
- [Conventional Commits Spec](https://www.conventionalcommits.org/)
