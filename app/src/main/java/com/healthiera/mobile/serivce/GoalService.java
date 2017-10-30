package com.healthiera.mobile.serivce;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.healthiera.mobile.entity.Goal;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Yengibar Manasyan
 * @date 10/18/16
 */
public class GoalService {

    public Long createGoal(Goal goal) {
        assertThat(goal).isNotNull();
        assertThat(goal.getId()).isNull();
        assertThat(goal.getName()).isNotNull().isNotEmpty();
        assertThat(goal.getValue()).isNotNull();
        final Long id = goal.save();
        assertThat(id).isNotNull().isGreaterThan(0L);

        return id;
    }

    public void updateGoal(Goal goal) {
        assertThat(goal).isNotNull();
        assertThat(goal.getId()).isNotNull();
        assertThat(goal.getName()).isNotNull().isNotEmpty();
        assertThat(goal.getValue()).isNotNull();
        new Update(Goal.class)
                .set("name ='"+goal.getName()+"' , value ='"+goal.getValue()+"'")
                .where("id = ?", goal.getId())
                .execute();
    }

    public List<Goal> getAllGoals() {
        final List<Goal> goals = new Select()
                .from(Goal.class)
                .execute();
        assertThat(goals).isNotNull();

        return goals;
    }

    public void deleteGoals(){
        final List<Goal> goals = new Delete()
                .from(Goal.class).execute();

    }

    public Goal getGoalByName(String name) {
        final Goal goals = new Select()
                .from(Goal.class)
                .where("name=?", name)
                .executeSingle();
        assertThat(goals).isNotNull();

        return goals;
    }

    public Goal getGoalByPosition(int position) {
        final Goal goals = (Goal)new Select()
                .from(Goal.class)
                .execute().get(position);
        assertThat(goals).isNotNull();

        return goals;
    }

    public List<String> getAllGoalsName() {
        final List<Goal> goals = new Select()
                .from(Goal.class)
                .execute();
        assertThat(goals).isNotNull();
        final List<String> goalsName =new ArrayList<>();
        for(int i = 0; i < goals.size(); i++){
            goalsName.add(goals.get(i).getName().toString()+" - "+ goals.get(i).getValue().toString());
        }

        return goalsName;
    }
}
