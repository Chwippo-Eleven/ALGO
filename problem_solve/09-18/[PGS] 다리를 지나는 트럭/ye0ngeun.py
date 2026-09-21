from collections import deque

def solution(bridge_length, weight, truck_weights):
    bridge = deque([0] * bridge_length)
    time = 0
    current_weight = 0
    trucks = deque(truck_weights)
    
    while trucks or any(bridge):
        time += 1
        
        out = bridge.popleft()
        current_weight -= out
        
        if trucks:
            next_truck = trucks[0]
            
            if current_weight + next_truck <= weight:
                now_truck = trucks.popleft()
                bridge.append(now_truck)
                current_weight += now_truck
            else:
                bridge.append(0)
        else:
            bridge.append(0)
            
    return time
        
