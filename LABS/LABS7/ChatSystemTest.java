package Napredno_Programiranje.LABS.LABS7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;

public class ChatSystemTest {

    public static void main(String[] args)  {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr.addUser(jin.next());
                if ( k == 1 ) cr.removeUser(jin.next());
                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if ( n == 0 ) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr2.addUser(jin.next());
                if ( k == 1 ) cr2.removeUser(jin.next());
                if ( k == 2 ) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if ( k == 1 ) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while ( true ) {
                String cmd = jin.next();
                if ( cmd.equals("stop") ) break;
                if ( cmd.equals("print") ) {
                    try {
                        System.out.println(cs.getRoom(jin.next())+"\n");
                    } catch (NoSuchRoomException ignored) {

                    }
                    continue;
                }
                for ( Method m : mts ) {
                    if ( m.getName().equals(cmd) ) {
                        Object[] params = new String[m.getParameterTypes().length];
                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                        try {
                            m.invoke(cs,params);
                        } catch (IllegalAccessException | InvocationTargetException ignored) {

                        }
                    }
                }
            }
        }
    }

}
class NoSuchRoomException extends Exception{
    public NoSuchRoomException(String message) {
        super(message);
    }
}
class NoSuchUserException extends Exception{
    public NoSuchUserException(String message) {
        super(message);
    }
}

class ChatSystem{
    Map<String ,ChatRoom> map;
    Set<String> users;
    public ChatSystem() {
        map = new TreeMap<>();
        users = new HashSet<>();
    }
    public void addRoom(String roomName){
        map.put(roomName,new ChatRoom(roomName));
    }
    public void removeRoom(String roomName){
        map.remove(roomName);
    }
    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
        if (!map.containsKey(roomName)) throw new NoSuchRoomException(roomName);
        return map.get(roomName);
    }
    public void register(String userName){
        users.add(userName);
         map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(ChatRoom::numUsers)))
                .findFirst().get().getValue().addUser(userName);
    }
    public void registerAndJoin(String userName, String roomName){
        users.add(userName);
        map.get(roomName).addUser(userName);
    }
    public void joinRoom(String userName, String roomName) throws NoSuchUserException, NoSuchRoomException {
        if (!map.containsKey(roomName))throw new NoSuchRoomException(roomName);
        map.get(roomName).addUser(users.stream().filter(u->u.equals(userName)).findFirst().orElseThrow(()-> new NoSuchUserException(userName)));
    }
    public void leaveRoom(String username, String roomName) throws NoSuchRoomException, NoSuchUserException {
        if (!map.containsKey(roomName))throw new NoSuchRoomException(roomName);
        boolean b =map.get(roomName).removeUser(username);
        if (!b) throw new NoSuchUserException(username);
    }
    public void followFriend(String username, String friend_username) throws NoSuchUserException {
        if (!users.contains(username))throw new NoSuchUserException(username);
        if (!users.contains(friend_username))throw new NoSuchUserException(username);
        map.values().stream()
                .filter(chatRoom -> chatRoom.hasUser(friend_username))
                .forEach(chatRoom -> chatRoom.addUser(username));
    }

}
class ChatRoom{
    String name;
    Set<String> users;

    public ChatRoom(String name) {
        this.name = name;
        users = new TreeSet<>();
    }
    public void addUser(String user){
        users.add(user);
    }
    public boolean removeUser(String username){
        return users.remove(username);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
         users.forEach(user->sb.append(user).append("\n"));
        if (users.isEmpty())sb.append("EMPTY").append("\n");
        return sb.toString();
    }
    public boolean hasUser(String username){
        return users.contains(username);
    }
    public int numUsers(){
        return users.size();
    }
}
















