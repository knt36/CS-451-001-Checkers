# CS-451-001-Checkers
A Networked Checkers Game. All documentation and no code makes Jack a Software Engineer!

# Group Members

* Mark Blaho (cpb49@drexel.edu)
* Khoi Tran (knt36@drexel.edu)
* Rachel Goeken (rmg92@drexel.edu)
* Max Mattes (mm3888@drexel.edu)

## Our Work Flow

We will be following a model of forks and branches. We each work on our own fork, and only Mark (who's managing operations) can merge to the master repo. If your git-fu fails you and everything is completely messed up, it won't affect anyone else this way, and you can always do a clean pull from master to reset everything.

### First Steps

1. Click the button at the top of this repository that says "Fork". This will create your own personal fork where your (or our) git-fu failures are insulated away from the rest of the team. You'll notice the repository URL is https://github.com/<**YOUR USERNAME**>/CS-451-001-Checkers so use this information to check your commands in the following steps
1. Clone your personal fork to your programming device (hopefully a real computer of some sort and not like a phone). On the commandline (which I'm going to assume you're using), this is `git clone https://github.com/<**YOUR USERNAME**>/CS-451-001-Checkers.git`. It may ask for credentials or other info and it's up to you to decide if/how to handle that.
1. Add upstream as a remote target (aka you can push/pull to it). The main repository will be called "upstream" and is added to your environment using `git remote add upstream https://github.com/MBLAHO13/CS-451-001-Checkers.git` **REMEMBER, NEVER EVER EVER DO SOMETHING THAT LOOKS LIKE `push upstream`**

*Please, please PLEASE do not merge your PRs to Master.* This is Mark's job, because munging the repo will just slow everyone down. Mark is the Benevolent Dictator :innocent:

Don't forget to set your name and email on your local machine as well! Just a nice thing to do. [Documentation on how to do that is here](https://help.github.com/articles/setting-your-email-in-git/)

We'll also be using the branching model. This means that every discrete code chunk gets its own branch with a descriptive name. (This commit is located in the `Readme-Changes-Example` branch as an example.) 

After a branch is created, you can push as many commits to it as you want. Once you're satisfied with it, and Travis (or a similar unit tester) is reporting success, create your pull request. ([Github Documentation](https://help.github.com/articles/using-pull-requests/)) Once someone else in the group looks over the changes and says it's good, the PR will be merged into master by Mark. **All PRs must be peer-reviewed before merging, with at least one other person looking at the code and saying it's good. This ensures we don't produce :hankey:!** For an example of this, check the PR request for `Readme-Changes-Example`, which is #1 .

# Links to look at:

* What the hell is git?! : https://rogerdudler.github.io/git-guide/
* Branch Management: https://git-scm.com/book/en/v2/Git-Branching-Branch-Management
* So You Have a Mess On Your Hands (The definitive guide to fixing your oops): http://justinhileman.info/article/git-pretty/

## Planned Infrastructure

* Github Issues :heavy_check_mark:
* Github VCS :heavy_check_mark:
* Content Requests (Provided by Github Issues) :heavy_check_mark:
* Travis Continuous Integration :white_medium_square:
* Code Coverage Tool :white_medium_square:
* Static Analysis Tool :white_medium_square:
