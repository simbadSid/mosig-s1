# include "stream.h"



//----------------------------------------
// Allocates memory for a stream structure.
// The pointed memory space is filled with zeros.
//----------------------------------------
stream *newStream()
{
	stream *res = malloc(sizeof(stream));

	if (res == NULL) return NULL;							// Case memory overflow
	memset(res, 0, sizeof(stream));
	return res;
}


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
char streamOpen(char *fileName, char accessRight, stream *result)
{
	if ((accessRight != STREAM_READ_RIGHT) &&
		(accessRight != STREAM_WRITE_RIGHT))	return SRTEAM_ACCESS_RIGHT_ERROR;			// Case: wrong access right parameter
	int fd = open(fileName, accessRight, 0);
	if (fd == -1)								return STREAM_OPEN_ERROR;					// Case: error while opening the file
	result->fd			= fd;
	result->accessRight	= accessRight;
	result->buffer		= new_list();
	return STREAM_OK;
}

//----------------------------------------
// Reads a single character from the given stream.
// This character is put into the output parameter res.
// The stream is updated.
// Return:
//	- STREAM_OK:			if the character has been red successfully.
//	- STREAM_END_OF_FILE	if the stream has no remaining characters.
//	- STREAM_READ_FORBIDDEN	if the stream access rights does not include reading.
//----------------------------------------
int streamReadChar(stream *stream, char *res)
{
	int i, size;
	char *str;

	if (0 == (stream->accessRight & STREAM_READ_RIGHT)) return STREAM_READ_FORBIDDEN;
	if (list_size(stream->buffer) == 0)
	{
		str = malloc(sizeof(char)*STREAM_BUFFER_SIZE);
		size = read(stream->fd, str, STREAM_BUFFER_SIZE);
		if (size <= 0) return STREAM_END_OF_FILE;
		for (i=0; i<size; i++)
		{
			list_append(stream->buffer, str);
			str += sizeof(char);
		}
		for (i=size; i<STREAM_BUFFER_SIZE; i++)
		{
			free(str);
			str += sizeof(char);
		}
	}
	str = list_element_at(stream->buffer, 0);
	*res = *str;
	list_remove_at_index(stream->buffer, 0);
	return STREAM_OK;
}
//----------------------------------------
// Reads "size" characters from the given stream.
// This characters are put into the output parameter res.
// The stream is updated.
// Return:
//	- STREAM_END_OF_FILE	if the stream has no remaining characters.
//	- STREAM_READ_FORBIDDEN	if the stream access rights does not include reading.
//	- The number of red characters otherwise.
//----------------------------------------
int streamReadString(stream *stream, char *res, int size)
{
	char *str = res;
	int i, test;

	if (0 == (stream->accessRight & STREAM_READ_RIGHT)) return STREAM_READ_FORBIDDEN;

	for (i=0; i<size; i++)
	{
		test = streamReadChar(stream, str);
		if (test == STREAM_END_OF_FILE)
		{
			if (i == 0) return STREAM_END_OF_FILE;
			else		return i;
		}
		str += sizeof(char);
	}
	return size;
}
//----------------------------------------
// Writes the single character c into the given stream.
// The stream is updated.
// Return:
//	- STREAM_OK:				if the character has been written successfully.
//	- STREAM_WRITE_FORBIDDEN	if the stream access rights does not include writing.
//----------------------------------------
int streamWriteChar(stream *stream, char c)
{
	if (0 == (stream->accessRight & STREAM_WRITE_RIGHT)) return STREAM_WRITE_FORBIDDEN;

	char *elem = malloc(sizeof(char));
	*elem = c;
	list_append(stream->buffer, elem);
	if (list_size(stream->buffer) == STREAM_BUFFER_SIZE) emptyWriteBuffer(stream);
	return STREAM_OK;
}
//----------------------------------------
// Writes "size" characters from the given string to the given stream.
// The stream is updated.
// Return:
//	- STREAM_WRITE_FORBIDDEN	if the stream access rights does not include writing.
//	- The number of red characters otherwise.
//----------------------------------------
int streamWriteString(stream *stream, char *str, int size)
{
	int i;
	char *str0 = str;

	if (0 == (stream->accessRight & STREAM_WRITE_RIGHT)) return STREAM_WRITE_FORBIDDEN;

	for (i=0; i<size; i++)
	{
		streamWriteChar(stream, *str0);
		str0 += sizeof(char);
	}
	return size;
}
//----------------------------------------
// If the write buffer is not empty, flush the buffer in the file.
// Close the opened stream.
// Remove all the allocated spaces.
//----------------------------------------
void streamClose(stream *stream)
{
	int size = list_size(stream->buffer);

	if ((size > 0) && (0 != (stream->accessRight & STREAM_WRITE_RIGHT))) emptyWriteBuffer(stream);
	close(stream->fd);
	list_remove_all(stream->buffer);
}
//----------------------------------------
// Empty the current stream buffer into the corresponding file.
// The buffer must have been opened with write access rights.
// Return:
//	- STREAM_WRITE_FORBIDDEN:	if the stream has not been opened with write access rights
//	- STREAM_OK:				if the buffer flush has been made successfully.
//----------------------------------------
int emptyWriteBuffer(stream *stream)
{
	int size = list_size(stream->buffer), i, test;
	char*str, *elem, *elem0;

	if (0 == (stream->accessRight & STREAM_WRITE_RIGHT))	return STREAM_WRITE_FORBIDDEN;
	if (size == 0)											return STREAM_OK;

	str = malloc(sizeof(char) * size);
	elem = str;
	for (i=0; i<size; i++)
	{
		elem0 = list_element_at(stream->buffer, 0);
		*elem = *elem0;
		elem += sizeof(char);
		list_remove_(stream->buffer, 0);
	}
	test = write(stream->fd, str, size);
	free(str);
	if (test != size)
	{
		printf("*** emptyWriteBuffer: an error occurred while writing the buffer content into the file ***\n");
		exit(0);
	}
	return STREAM_OK;
}
