/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entities;

/**
 *
 * @author Aymen
 */
public class EventReaction {
    
    public enum Reaction {
        like,
        dislike
    }
    private int event_id, user_id;
    private Reaction reaction;

    public EventReaction() {
    }

    public EventReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    public EventReaction(int event_id, int user_id, Reaction reaction) {
        this.event_id = event_id;
        this.user_id = user_id;
        this.reaction = reaction;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    @Override
    public String toString() {
        return "EventReaction{" + "event_id=" + event_id + ", user_id=" + user_id + ", reaction=" + reaction + '}';
    }
    
    
}
