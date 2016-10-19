/*
 * stream.h
 *
 *  Created on: Sep 17, 2015
 *      Author: ogruber
 */

#ifndef STREAM_H_
#define STREAM_H_

struct stream;

/**
 * Allocates a new stream.
 * If the given max size is greater than zero,
 * the stream will limit its memory footprint to the
 * given maximum size.
 */
extern struct stream* new_stream(int max_size);

/*
 * Free a stream. The stream may be empty or not.
 */
extern void free_stream(struct stream*);

/*
 * Returns the available number of characters.
 */
extern int stream_available(struct stream* stream);

/*
 * Attempts to read the requested number of characters (nchars).
 * The read operations may read less than requested, but never more.
 * The read operation returns the actual number of characters read.
 * The operation never blocks or waits for characters.
 * The operation is not thread safe.
 */
extern int stream_read(struct stream* stream, char *chars, size_t nchars);

/*
 * Attempts to write the requested number of characters (nchars).
 * The write operations may write less than requested, but never more.
 * The write operation returns the actual number of characters read.
 * The operation never blocks or waits for room.
 * The ownership of the character array remains with the caller,
 * it is safe to reuse or free right after the call to write returns.
 */
extern int stream_write(struct stream* stream, char *chars, size_t nchars);

#endif /* STREAM_H_ */
