# Private Git Server

## Initialize Server

Create the `git` user on the server. Run the [setup script](setup/server.sh) if
for setting up a server on Ubuntu.

## Set up local SSH configuration

Add this setting to the local SSH configuration at `~/.ssh/config`:

    Host tcs
        User git
        HostName thecodesmith.com

Of course, replace the `HostName` as necessary. The `Host` name can be set to
anything you like. I like abbreviations.

## Add local SSH public key to the server's authorized keys

Run this command, and enter the password created when setting up the `git` user
on the server:

    ssh-copy-id tcs

Note: On Mac OS X, there is no ssh-copy-id command, so an alternative is this:

    cat ~/.ssh/id_rsa.pub | ssh tcs "mkdir ~/.ssh; cat >> ~/.ssh/authorized_keys"

## Test Connection

Test the new Git server with the command:

    git-create tcs:hello-world

This should create a repo called `hello-world` on the `tcs` server. It can now be used
like any other Git remote, for example pushing from a local repository:

    git remote add tcs tcs:hello-world
    git push -u tcs master

Or to clone the repository:

    git clone tcs:hello-world
