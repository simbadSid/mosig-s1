# mosig-s1

# Git tutorial (For my students)

## Create repo
	### 1- Create new repo on github

	### 2- Create a directory with same name
	mkdir <repoName>
	cd <repoName>

	### 3- Initialize the local repo and add my files
	git init .
	git add .

	### 4- Commit the added files
	git commit -m "Repo creation"

	### 5- Link the local (on my machine) repo to the remote (github) repo
	git remote add origin <remoteRepositoryURL>	# Link to the github URL
	git remote -v					# Verifies the new remote URL

	### 6- Push the changes fron my local repo to github
	git push origin master


## Update repo in current branch
	### 1- Check the changes on my current branch (local machine)
	git status

	### 2- Add the files that have been created/changed to my current branch (local machine)
	git add -A					# or replace -A by file names
	
	git add . 					# stages new and modified, without deleted
	git add -u					# stages modified and deleted, without new



	### 3- Commit the changes (on local machine)
	git commit -m "Message"
	git commit -a to commit only the modified and deleted files

	### 4- Pull the changes (probably made by co-workers) and solve conflict
	git pull

	### 5- Push local commits to github
	git push

## Creat and work on new branch
	### 1- List all existing branches
	git branch

	### 2- Create a new branch
	git branch <newBranchName>

	### 3- Change the local branch to a new branch
	git checkout <newBranchName>
	
	### 4- Do work on new branch and commit/push it

## Merge new branch to an old one
	### 1- Go to the branch that receives the merge
	git checkout <oldBranchName/master>

	### 2- Merge local branch to the new branch
	git merge <newBranchName>

	### 3- Delete the created branch
	git branch -d <newBranchName>

