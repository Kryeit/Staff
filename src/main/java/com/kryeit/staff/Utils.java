package com.kryeit.staff;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.minecraft.server.command.ServerCommandSource;

public class Utils {

    public static boolean check(ServerCommandSource source, String permission, boolean defaultValue) {
        User user = LuckPermsProvider.get().getUserManager().getUser(source.getPlayer().getUuid());

        if (user == null) {
            return defaultValue;
        }

        return user.getNodes(NodeType.PERMISSION).stream()
                .filter(NodeType.PERMISSION::matches)
                .map(NodeType.PERMISSION::cast)
                .anyMatch(node -> node.getPermission().equalsIgnoreCase(permission) && node.getValue());
    }
}
