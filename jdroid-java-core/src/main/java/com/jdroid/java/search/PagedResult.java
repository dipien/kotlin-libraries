package com.jdroid.java.search;

import com.jdroid.java.collections.Lists;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a list result in a paginated API call.
 *
 * @param <T> The list item.
 */
public class PagedResult<T> {

	private Boolean lastPage;
	private List<T> results;

	public PagedResult(List<T> results, Boolean lastPage) {
		this.results = results;
		this.lastPage = lastPage;
	}

	/**
	 * @param lastPage Whether the paginates list contains the last page or not.
	 */
	public PagedResult(Boolean lastPage) {
		this(Lists.INSTANCE.<T>newArrayList(), lastPage);
	}

	public PagedResult(List<T> results) {
		this(results, true);
	}

	public PagedResult() {
		this(true);
	}

	/**
	 * Adds a result item to the list.
	 *
	 * @param result The result to add.
	 */
	public void addResult(T result) {
		results.add(result);
	}

	/**
	 * Adds result items to the list.
	 *
	 * @param results The results to add.
	 */
	public void addResults(Collection<T> results) {
		this.results.addAll(results);
	}

	/**
	 * @return the results
	 */
	public List<T> getResults() {
		return results;
	}

	public void sortResults(Comparator<T> comparator) {
		Collections.sort(results, comparator);
	}

	/**
	 * @return the lastPage
	 */
	public boolean isLastPage() {
		return lastPage;
	}
}
