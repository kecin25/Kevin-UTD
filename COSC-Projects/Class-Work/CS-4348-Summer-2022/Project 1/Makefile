BUILD=build
main: $(BUILD)/main.o dir1 dir2
	gcc -std=c99  -D_SVID_SOURCE $(BUILD)/main.o -o main

$(BUILD)/main.o: main.c build dir1/atest2.txt dir1/dir1_test1.txt dir2/dir2_test1.txt
	gcc -std=c99 -D_SVID_SOURCE -c main.c -o $@

build:
	mkdir -p $(BUILD)
	
dir1/atest2.txt: dir1
	touch dir1/atest2.txt
	echo "This is a second file" > dir1/atest2.txt

dir1/dir1_test1.txt: dir1
	touch dir1/dir1_test1.txt
	echo "text line 1" > dir1/dir1_test1.txt
	echo "hello there" >> dir1/dir1_test1.txt
	echo "line3" >> dir1/dir1_test1.txt
	echo "done" >> dir1/dir1_test1.txt
	
dir1:
	mkdir -p dir1

dir2/dir2_test1.txt: dir2
	touch dir2/dir2_test1.txt
	echo "different stuff in this file" > dir2/dir2_test1.txt
	echo "goodbye" >> dir2/dir2_test1.txt
	echo "not the end" >> dir2/dir2_test1.txt
	
dir2:
	mkdir -p dir2

clean:
	rm -rf $(BUILD) 
	rm -rf main

clean_all: clean
	rm -r -f dir1 dir2