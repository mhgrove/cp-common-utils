// Copyright (c) 2010 - 2012 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.complexible.common.base;

/**
 * <p>The type of a {@link Change}.  Concrete change types should be enums; this is enforced by
 * {@link Change#of(Enum, Object) Change creation} and is in place to promote the serilizability of
 * ChangeType objects, and indirectly, Changes.</p>
 *
 * @author  Michael Grove
 * @since   1.0
 * @version 1.0
 */
public interface ChangeType {
}
