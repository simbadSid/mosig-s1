#ifndef STREAM_H_
#define STREAM_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>
#include "list.h"






typedef struct
{
	int			fd;					// File descriptor
	char		accessRight;		// List of the access rights: combination of the macros STREAM_XXX_RIGHT
	struct list	*buffer;			// List of characters to write in the file after reaching the list
} stream;


#define STREAM_OK					1
#define STREAM_OPEN_ERROR			-1
#define SRTEAM_ACCESS_RIGHT_ERROR	-2
#define STREAM_END_OF_FILE			-3
#define STREAM_READ_FORBIDDEN		-4
#define STREAM_WRITE_FORBIDDEN		-5

#define STREAM_READ_RIGHT			1
#define STREAM_WRITE_RIGHT			2

#define STREAM_BUFFER_SIZE			8



//----------------------------------------
// Allocates memory for a stream structure.
// The pointed memory space is filled with zeros.
//----------------------------------------
extern stream *newStream();
//----------------------------------------
// Open the file described by the given file name using.
// the given addresses. The output parameter result is filled.
// Input parameters:
//		- fileName:				String representing the name of the file.
//		- accessRight:			One of the macros STREAM_XXX_RIGHT (no combination is allowed).
// Output parameter:
//		- result:				stream to be filed.
// Return:
//		- STREAM_OK:			in case of success.
//		- STREAM_OPEN_ERROR:	if the file opening failed.
//		- SRTEAM_ACCESS_RIGHT_ERROR	if the input access rights are wrong.
//----------------------------------------
extern char streamOpen(char *fileName, char accessRight, stream *result);
//----------------------------------------
// Reads a single character from the given stream.
// This character is put into the output parameter res.
// The stream is updated.
// Return:
//	- STREAM_OK:			if the character has been red successfully.
//	- STREAM_END_OF_FILE	if the stream has no remaining characters.
//	- STREAM_READ_FORBIDDEN	if the stream access rights does not include reading.
//----------------------------------------
extern int streamReadChar(stream *stream, char *res);
//----------------------------------------
// Reads "size" characters from the given stream.
// This characters are put into the output parameter res.
// The stream is updated.
// Return:
//	- STREAM_END_OF_FILE	if the stream has no remaining characters.
//	- STREAM_READ_FORBIDDEN	if the stream access rights does not include reading.
//	- The number of red characters otherwise.
//----------------------------------------
extern int streamReadString(stream *stream, char *res, int size);
//----------------------------------------
// Writes the single character c into the given stream.
// The stream is updated.
// Return:
//	- STREAM_OK:				if the character has been written successfully.
//	- STREAM_WRITE_FORBIDDEN	if the stream access rights does not include writing.
//----------------------------------------
extern int streamWriteChar(stream *stream, char c);
//----------------------------------------
// Writes "size" characters from the given string to the given stream.
// The stream is updated.
// Return:
//	- STREAM_WRITE_FORBIDDEN	if the stream access rights does not include writing.
//	- The number of red characters otherwise.
//----------------------------------------
extern int streamWriteString(stream *stream, char *str, int size);
//----------------------------------------
// If the write buffer is not empty, flush the buffer in the file.
// Close the opened stream.
// Remove all the allocated spaces.
//----------------------------------------
extern void streamClose(stream *stream);
//----------------------------------------
// Empty the current stream buffer into the corresponding file.
// The buffer must have been opened with write access rights.
// Return:
//	- STREAM_WRITE_FORBIDDEN:	if the stream has not been opened with write access rights
//	- STREAM_OK:				if the buffer flush has been made successfully.
//----------------------------------------
extern int emptyWriteBuffer(stream *stream);


#endif
