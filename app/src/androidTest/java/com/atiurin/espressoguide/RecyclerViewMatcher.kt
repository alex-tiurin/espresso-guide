package com.atiurin.espressoguide

import android.content.res.Resources
import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.util.ArrayList
import android.R.attr.description


class RecyclerViewMatcher(val recyclerViewMatcher: Matcher<View>) {
    var recyclerView: RecyclerView? = null
    open fun atItem(itemMatcher: Matcher<View>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var itemView : View? = null
            override fun describeTo(description: Description?) {
                if (recyclerView == null) {
                    description?.appendText("No matching recycler view with : [$recyclerViewMatcher]. ")
                    return
                }
                description?.appendText("Found recycler view matches : [$recyclerViewMatcher]. ")
                if (itemView == null){
                    description?.appendText("No matching recycler view item with : [$itemMatcher]")
                    return
                }
            }

            override fun matchesSafely(view: View?): Boolean {
                itemView = findItemView(itemMatcher, view?.rootView)
                return if (itemView != null) {
                    itemView == view
                } else false
            }
        }
    }

    open fun atItemChild(itemMatcher: Matcher<View>, childMatcher: Matcher<View>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var itemView : View? = null
            var searchedView: View? = null
            override fun describeTo(description: Description?) {
                if (recyclerView == null) {
                    description?.appendText("No matching recycler view with : [$recyclerViewMatcher]. ")
                    return
                }
                description?.appendText("Found recycler view matches : [$recyclerViewMatcher]. ")
                if (itemView == null){
                    description?.appendText("No matching recycler view item with : [$itemMatcher]")
                    return
                }
                description?.appendText("Found recycler view item matches : [$itemMatcher]. ")
                if (searchedView == null){
                    description?.appendText("No matching item child view with : [$childMatcher]")
                    return
                }
            }

            override fun matchesSafely(view: View?): Boolean {
                itemView = findItemView(itemMatcher, view?.rootView)
                if (itemView != null) {
                    for (childView in TreeIterables.breadthFirstViewTraversal(itemView)) {
                        if (childMatcher.matches(childView)) {
                            searchedView = childView
                            break
                        }
                    }
                }
                return if (searchedView != null) {
                    searchedView == view
                } else false
            }
        }
    }

    private fun findItemView(itemMatcher: Matcher<View>, rootView: View?): View? {
        for (childView in TreeIterables.breadthFirstViewTraversal(rootView)) {
            if (recyclerViewMatcher.matches(childView)) {
                val recyclerView = childView as RecyclerView
                this.recyclerView = recyclerView//для описания ошибки
                val viewHolderMatcher: Matcher<RecyclerView.ViewHolder> = viewHolderMatcher(itemMatcher)
                val matchedItems: List<MatchedItem> = itemsMatching(recyclerView, viewHolderMatcher, 1)
                if (matchedItems.isEmpty()) return null
                return recyclerView.findViewHolderForAdapterPosition(matchedItems[0].position)?.itemView
            }
        }
        return null
    }
}

fun withRecyclerView(recyclerViewMatcher: Matcher<View>): RecyclerViewMatcher {
    return RecyclerViewMatcher(recyclerViewMatcher)
}

private fun <T : VH, VH : RecyclerView.ViewHolder> itemsMatching(
    recyclerView: RecyclerView, viewHolderMatcher: Matcher<VH>, max: Int
): List<MatchedItem> {
    val adapter = recyclerView.adapter
    val viewHolderCache = SparseArray<VH>()
    val matchedItems = ArrayList<MatchedItem>()
    if (adapter == null) return matchedItems
    for (position in 0 until adapter.itemCount) {
        val itemType = adapter.getItemViewType(position)
        var cachedViewHolder: VH? = viewHolderCache.get(itemType)
        // Create a view holder per type if not exists
        if (cachedViewHolder == null) {
            cachedViewHolder = adapter.createViewHolder(recyclerView, itemType) as VH?
            viewHolderCache.put(itemType, cachedViewHolder)
        }
        // Bind data to ViewHolder and apply matcher to view descendants.
        adapter.bindViewHolder((cachedViewHolder as T?)!!, position)
        if (viewHolderMatcher.matches(cachedViewHolder)) {
            matchedItems.add(
                MatchedItem(
                    position,
                    HumanReadables.getViewHierarchyErrorMessage(
                        cachedViewHolder!!.itemView, null,
                        "\n\n*** Matched ViewHolder item at position: $position ***", null
                    )
                )
            )
            if (matchedItems.size == max) {
                break
            }
        }
    }
    return matchedItems
}

/**
 * Wrapper for matched items in recycler view which contains position and description of matched
 * view.
 */
private class MatchedItem(val position: Int, val description: String) {

    override fun toString(): String {
        return description
    }
}

/**
 * Creates matcher for view holder with given item view matcher.
 *
 * @param itemViewMatcher a item view matcher which is used to match item.
 * @return a matcher which matches a view holder containing item matching itemViewMatcher.
 */
private fun <VH : RecyclerView.ViewHolder> viewHolderMatcher(
    itemViewMatcher: Matcher<View>
): Matcher<VH> {
    return object : TypeSafeMatcher<VH>() {
        override fun matchesSafely(item: VH): Boolean {
            return itemViewMatcher.matches(item.itemView)
        }

        override fun describeTo(description: Description) {
            description.appendText("holder with view: ")
            itemViewMatcher.describeTo(description)
        }
    }
}