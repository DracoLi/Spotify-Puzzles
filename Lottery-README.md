## Lottery

#### There are two ways to approach this problem

#### 1. Calculate the group's permutations of winning then divide it by the total permutations.
Assuming that 100 peoples are in the lottery (m = 100), there are 10 possible winners (n = 10), each person can buy 1 ticket (t = 1) and we we only have 1 person in the group (p = 1).
In this case in order for the group to win they have to win at least once. They can win more but since there's only one person in the group they can max win once.
So to calculate the team's permutation of winning exactly once we must calculate the possibility of choosing 9 spots among 99 people. The reason for this is because if we assume that we won, then there can only be 99 more people left with 9 more chances of winning. Thus 99 choose 9 is the number of ways that we can win out of all the possibilities of the lottery.
So if we can win 99 choose 9 ways out of 100 choose 10 ways then our probability of winning is 99 choose 9 over 100 choose 10.
This result gives us our changes of winning given that we only have 1 person in the group. If we have more people then we need to multiply our total # of ways of winning for one person by the number of ways that our group can win. This means multiplying the our last number by the # of people in group choose # of times we need to win to figure out the # of ways we can win within the group.

#### 2. Calculate the group's chances of loosing then divide it by total permutations of the problem.
Similar approach to above question except that we need to choose the number of times we can potentially loose. So for example instead of using 99 choose 9 ways for us to win, we must now do 99 choose 10 because this assumes that we are not part of the 10 winning tickets.