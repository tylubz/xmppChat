package net.tylubz.chat.shared.utils;

import net.tylubz.chat.shared.model.JidContact;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Lebedev
 */

public class ModelUtils {
    public static List<String> toStringList(List<JidContact> jidContactList) {
        List<String> stringList = new ArrayList<>();
        for(JidContact jidContact : jidContactList) {
            stringList.add(jidContact.getJid());
        }
        return stringList;
    }
}
