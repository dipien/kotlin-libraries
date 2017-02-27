package com.jdroid.java.concurrent;

public class NormalPriorityThreadFactory extends AbstractThreadFactory {
	
	public NormalPriorityThreadFactory(String namePrefix) {
		super(namePrefix);
	}
	
	public NormalPriorityThreadFactory() {
		super("normal-prio");
	}
	
	@Override
	protected int getThreadsPriority() {
		return Thread.NORM_PRIORITY;
	}
}