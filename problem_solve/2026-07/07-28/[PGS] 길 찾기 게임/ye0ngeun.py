import sys

sys.setrecursionlimit(10**5)

class Node:
    def __init__(self, number, x, y):
        self.number = number
        self.x = x
        self.y = y
        self.left = None
        self.right = None

def solution(nodeinfo):

    # 노드 삽입 (트리생성)
    def insert(root, node):
        current = root

        while True:
            if node.x < current.x:
                if current.left is None:
                    current.left = node
                    return

                current = current.left

            else:
                if current.right is None:
                    current.right = node
                    return

                current = current.right

    # 전위순회
    def preorder(node):
        if node is None:
            return

        pre.append(node.number)
        preorder(node.left)
        preorder(node.right)

    # 후위순회
    def postorder(node):
        if node is None:
            return

        postorder(node.left)
        postorder(node.right)
        post.append(node.number)

    # 코드 시작
    pre = []
    post = []
    nodes = []

    for number, (x, y) in enumerate(nodeinfo, start=1):
        nodes.append(Node(number, x, y))

    nodes.sort(key=lambda node: (-node.y, node.x))

    root = nodes[0]

    for node in nodes[1:]:
        insert(root, node)

    preorder(root)
    postorder(root)

    return [pre, post]
