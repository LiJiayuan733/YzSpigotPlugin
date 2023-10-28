package org.thzs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class YzTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        String cmd=command.getName();
        if(cmd.equals("yz")){
            if (strings.length == 1){
                return level2(strings[0]);
            }else if(strings.length == 2){
                return level3(strings[1]);
            }
        }
        return null;
    }
    public List<String> level2(String l){
        String[] list={"gui","help"};
        List<String> list1=new ArrayList<>();
        for (String s:list) {
            if(s.startsWith(l)){
                list1.add(s);
            }
        }
        return list1;
    }
    public List<String> level3(String l){
        String[] list={"send","test"};
        List<String> list1=new ArrayList<>();
        for (String s:list) {
            if(s.startsWith(l)){
                list1.add(s);
            }
        }
        return list1;
    }
}
