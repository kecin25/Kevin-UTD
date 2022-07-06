main:
	gcc -std=c99 -D_SVID_SOURCE -c Client_test.c -o client
	gcc -std=c99 -D_SVID_SOURCE -c test.c -o server
	chmod u+x server
	chmod u+x client

clean:
	rm client server