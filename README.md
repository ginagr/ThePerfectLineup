#The Perfect Lineup

##How to install the app using Android Studio

In order to make the app work for development, do the following steps:

1. Open Android Studio

2. Press "Check Out Project from Version Control (or Version Control System [VCS])"

3. Choose GitHub, follow the steps and choose this repository as the source (http://github.com/ginagr/ThePerfectLineup.git)

4. Choose the Parent directory in which you want to clone the Repository

5. Name it Appropriately (e.g. ThePerfectLineup)

6. Click Clone

7. When prompted to open the Project, press Yes

8. In the Import Project from Gradle prompt, make sure "Use default gradle wrapper is selected" and press OK

    (If Android Studio tells you "Unregistered root detected" press EventLog [lower right corner] - > Add Root)

9. Double check that everything is fine and that all files are excluded by using a command line tool, navigating to your directory (e.g. cd code/ThePerfectLineup) and running the command
```
git status
```
Nothing should show up, it should merely tell you taht your project is up to date and that your working directory is clean.

10. Run the project!

    [You must have Git installed on your computer to use the VCS]

##Making changes
 
Your default branch should be develop. Our branch structure is as follows:


  
    0------------master-------------------------------------------------------------------------->
      \----------develop--------------------------------------------------------------------/----------->
        \            \--feature 2--------------/------------/
         \                                    /
          \-----------------feature 1 -------/

#####master: 
Has periodical, stable releasses (eg. v 1.0.0)

#####develop:
Is the ongoing development branch. Periodically merged with master. All merge conflicts are resolved on this branch. Make sure to fetch often.

#####feature branches:
All code is written on these branches. Features have to work before merging them with develop. Features also include bugfixes and similar things. All features relate to a specific issue on GitHub, as mentioned in Section 4. It is _recommended_ to name the branch after the following convention:

1. which issue does it relate to by id, e.g. number 1 maps to dropping issue - crash when dropping on dropped athlete #1

2. a descriptive name, divided by dashes (e.g. dropping-issue)

In total, we can name the feature branch:

```
1-dropping-issue
```

###How to make new branches to add a Feature/ Fix a bug etc

In order to add a feature, go onto GitHub and make sure it is marked in our issues. You can assign a particular issue to yourself or just add it to the list so someone else can work on it at some point.

On your local machine, make sure you are on the develop branch and run the following code:

```
git checkout -b <NAME OF YOUR NEW BRANCH>
```

This will generate a new local branch. Now you can work on it (or at least make a single change.)

Then run:

```
git add <FILE YOU CHANGED>
```

Alternatively you can use 'git add --all' to add all files to your working directory. Now run:

```
git commit -m "Comment that describes what I did in this change"
```

and then:

```
git push --set-upstream origin <NAME OF YOUR BRANCH>
```


Alternatively you can create a branch in GitHub online.


###What to avoid

Never run git commands you do not know. Always do a quick search first or ask. This applies in particular to commands such as 'git clean'




