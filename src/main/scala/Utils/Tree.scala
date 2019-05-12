package com.github.scalamaze.utils

/** A tree. */
class Tree {
  var parent: Option[Tree] = None

  @annotation.tailrec
  private def go(tree: Tree): Tree = if (tree.parent.isDefined) go(tree.parent.get) else tree

  /** Get the root of the tree */
  def root: Tree = go(this)

  /** Return true if a two tree have the same root */
  def isConnectedTo(tree: Tree): Boolean = this.root == tree.root

  /** Connect this to a another tree */
  def connectTo(tree: Tree): Unit = tree.root.parent = Some(this)

}
