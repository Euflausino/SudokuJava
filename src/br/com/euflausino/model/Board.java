package br.com.euflausino.model;

import java.util.Collection;
import java.util.List;

import static br.com.euflausino.model.GameStateEnum.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {
    private final List<List<Space>> spaces;

    public Board(List<List<Space>> space) {
        this.spaces = space;
    }

    public List<List<Space>> getSpace() {
        return spaces;
    }

    public GameStateEnum getStatus(){
        if(spaces.stream().flatMap(Collection::stream).noneMatch(s-> !s.isFixed() && nonNull(s.getValAtual()))){
            return NON_STARTED;
        }
        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getValAtual())) ? INCOMPLETE : COMPLETE;
    }
    public boolean hasErrors(){
        if(getStatus() == NON_STARTED){
            return false;
        }

        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s-> nonNull(s.getValAtual()) && !s.getValAtual().equals(s.getExpected()));
    }

    public boolean changeValue(final int col, final int row, final int value){
        var space = spaces.get(col).get(row);
        if(space.isFixed()){
            return false;
        }

        space.setValAtual(value);
        return true;
    }
    public boolean clearValue(final int col, final int row){
        var space = spaces.get(col).get(row);
        if(space.isFixed()){
            return false;
        }
        space.clearSpace();
        return true;
    }
    public void reset(){
        spaces.forEach(c-> c.forEach(Space::clearSpace));
    }

    public boolean gameIfFinixed(){
        return !hasErrors() && getStatus().equals(COMPLETE);
    }
}
