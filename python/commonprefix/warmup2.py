

# Write a function that takes a string message and a single character key.
# Encrypt the message by XORing each character with that key and return the result as a hex string.

def encrypt(msg, key):

    encrypt = ""
    key = ord(key)
    for i in range(len(msg)):
        encrypt += "%02x" % (ord(msg[i]) ^ key)
    return encrypt

if __name__ == "__main__":
    print(encrypt("test", "Y"))
