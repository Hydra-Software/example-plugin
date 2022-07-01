package org.example.plugin.command;

import com.hydraclient.hydra.api.command.Command;

public class ExampleCommand implements Command {

    @Override
    public String getName() {
        return "ExampleCommand"; // name of the command that will be shown to user
    }

    @Override
    public boolean run(String[] args) { // args [0] is the command name
        if(args.length < 2) {
            // return false for invalid input (if the usage text should be sent)
            // if the command failed but had correct input, return true
            return false;
        }

        return true;
    }

    @Override
    public String[] usage() {
        return new String[]{"examplecommand [value]"}; // text that will be sent if the command is executed with invalid input
    }

    @Override
    public String[] description() {
        return new String[]{"Does a thing"}; // text for -help
    }
}
