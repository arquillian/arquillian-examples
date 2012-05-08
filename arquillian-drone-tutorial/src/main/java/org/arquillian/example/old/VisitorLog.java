/*
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.arquillian.example.old;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 */
public class VisitorLog {
    private List<String> visitors = new ArrayList<String>();
    
    public void addVisitor(String name) {
        visitors.add(name);
    }
    
    public void bootOutEveryone() {
        visitors.clear();
    }
    
    public int getVisitorCount() {
        return visitors.size();
    }
    
    public String createListOfNames() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = visitors.size(); i < len; i++) {
            sb.append(visitors.get(i));
            if (i < len - 2) {
                sb.append(", ");
            }
            else if (i == len - 2) {
                sb.append(" and ");
            }
        }
        
        return sb.toString();
    }
}
