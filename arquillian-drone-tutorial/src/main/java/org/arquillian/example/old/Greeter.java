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

import java.io.PrintStream;

import javax.inject.Inject;

/**
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 */
public class Greeter {
    @Inject
    private VisitorLog visitorLog;
    
    public void greet(PrintStream to, String name) {
        to.println(createGreeting(name));
        visitorLog.addVisitor(name);
    }
    
    public String createGreeting(String name) {
        return "Hello, " + name + "!";
    }
    
    public boolean hasVisitors() {
        return visitorLog.getVisitorCount() > 0;
    }
    
    public void farewellToAll(PrintStream to) {
        to.println(createFarewellToAll());
        visitorLog.bootOutEveryone();
    }
    
    public String createFarewellToAll() {
        return new StringBuilder("Goodbye, ")
                .append(visitorLog.createListOfNames())
                .append("!")
                .toString();
    }
}
