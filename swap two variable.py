# by temporary variable

x = int(input("enter first value"))
y = int(input("enter second value"))

temp = x
x= y
y = temp

print("swap value of x and y is ",x ,y)


# without temp

z = int(input("enter first value"))
v = int(input("enter second value"))

z,v = v,z

print("swap value of z and v is ",z,v)