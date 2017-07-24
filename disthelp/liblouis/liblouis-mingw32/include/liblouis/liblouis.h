/* liblouis Braille Translation and Back-Translation Library

Based on the Linux screenreader BRLTTY, copyright (C) 1999-2006 by The
BRLTTY Team

Copyright (C) 2004, 2005, 2006 ViewPlus Technologies, Inc. www.viewplus.com
Copyright (C) 2004, 2005, 2006 JJB Software, Inc. www.jjb-software.com
Copyright (C) 2016 Mike Gray, American Printing House for the Blind
Copyright (C) 2016 Davy Kager, Dedicon

This file is part of liblouis.

liblouis is free software: you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published
by the Free Software Foundation, either version 2.1 of the License, or
(at your option) any later version.

liblouis is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with liblouis. If not, see <http://www.gnu.org/licenses/>.

*/

/**
 * @file
 * @brief Public API of liblouis
 */

#ifndef __LIBLOUIS_H_
#define __LIBLOUIS_H_
#ifdef __cplusplus
extern "C" {
#endif /* __cplusplus */

typedef unsigned short int widechar;
typedef unsigned short formtype;

#ifdef _WIN32
#define EXPORT_CALL __stdcall
char *EXPORT_CALL lou_getProgramPath();
#else
#define EXPORT_CALL
#endif

typedef enum {
  plain_text = 0x0000,
  italic = 0x0001,     // emph_1
  underline = 0x0002,  // emph_2
  bold = 0x0004,       // emph_3
  emph_4 = 0x0008,
  emph_5 = 0x0010,
  emph_6 = 0x0020,
  emph_7 = 0x0040,
  emph_8 = 0x0080,
  emph_9 = 0x0100,
  emph_10 = 0x0200,
  computer_braille = 0x0400,
  no_translate = 0x0800,
  no_contract = 0x1000,
  // unused  0x2000,
  // used by syllable  0x4000,
  // used by syllable  0x8000
} typeforms;
#define comp_emph_1 italic
#define comp_emph_2 underline
#define comp_emph_3 bold

typedef enum {
  noContractions = 1,
  compbrlAtCursor = 2,
  dotsIO = 4,
  comp8Dots = 8,
  pass1Only = 16,
  compbrlLeftCursor = 32,
  otherTrans = 64,
  ucBrl = 128
} translationModes;

char *EXPORT_CALL lou_version();

/**
 * Return the size of widechar
 */
int EXPORT_CALL lou_charSize();

int EXPORT_CALL lou_translateString(const char *tableList,
                                    const widechar *inbuf, int *inlen,
                                    widechar *outbuf, int *outlen,
                                    formtype *typeform, char *spacing,
                                    int mode);

int EXPORT_CALL lou_translate(const char *tableList, const widechar *inbuf,
                              int *inlen, widechar *outbuf, int *outlen,
                              formtype *typeform, char *spacing, int *outputPos,
                              int *inputPos, int *cursorPos, int mode);

int EXPORT_CALL lou_translatePrehyphenated(const char *tableList,
                                           const widechar *inbuf, int *inlen,
                                           widechar *outbuf, int *outlen,
                                           formtype *typeform, char *spacing,
                                           int *outputPos, int *inputPos,
                                           int *cursorPos, char *inputHyphens,
                                           char *outputHyphens, int mode);

int EXPORT_CALL lou_hyphenate(const char *tableList, const widechar *inbuf,
                              int inlen, char *hyphens, int mode);
int EXPORT_CALL lou_dotsToChar(const char *tableList, widechar *inbuf,
                               widechar *outbuf, int length, int mode);
int EXPORT_CALL lou_charToDots(const char *tableList, const widechar *inbuf,
                               widechar *outbuf, int length, int mode);
int EXPORT_CALL lou_backTranslateString(const char *tableList,
                                        const widechar *inbuf, int *inlen,
                                        widechar *outbuf, int *outlen,
                                        formtype *typeform, char *spacing,
                                        int mode);

int EXPORT_CALL lou_backTranslate(const char *tableList, const widechar *inbuf,
                                  int *inlen, widechar *outbuf, int *outlen,
                                  formtype *typeform, char *spacing,
                                  int *outputPos, int *inputPos, int *cursorPos,
                                  int mode);
/**
 * Print error messages to a file
 *
 * @deprecated As of 2.6.0, applications using liblouis should
 * implement their own logging system.
 */
void EXPORT_CALL lou_logPrint(const char *format, ...);

/**
 * Specify the name of the file to be used by lou_logPrint.
 *
 * If it is not used, this file is stderr
 */
void EXPORT_CALL lou_logFile(const char *filename);

/**
 * Read a character from a file, whether big-encian, little-endian or ASCII8
 *
 * and return it as an integer. EOF at end of file. Mode = 1 on first
 * call, any other value thereafter
 */
int EXPORT_CALL lou_readCharFromFile(const char *fileName, int *mode);

/**
 * Close the log file so it can be read by other functions.
 */
void EXPORT_CALL lou_logEnd();

/**
 * Load and compile a translation table
 *
 * Check the table for errors. If none are found load the table into
 * memory and return a pointer to it. If errors are found return a
 * null pointer. It is called by lou_translateString() and
 * lou_backTranslateString() and also by functions in liblouisutdml
 * and by the tools.
 */
void *EXPORT_CALL lou_getTable(const char *tableList);

/**
 * Check a translation table for errors.
 *
 * If no errors are found it load the table into memory and returns a
 * non-zero value. Else the return value is 0.
 */
int EXPORT_CALL lou_checkTable(const char *tableList);

/**
 * Register a new table resolver. Overrides the default resolver. */
void EXPORT_CALL lou_registerTableResolver(
    char **(*resolver)(const char *table, const char *base));

/**
 * Compile a table entry on the fly at run-time
 *
 * This function enables you to compile a table entry on the fly at
 * run-time. The new entry is added to tableList and remains in
 * force until lou_free() is called. If tableList has not
 * previously been loaded it is loaded and compiled.
 *
 * @param inString contains the table entry to be added. It may be
 * anything valid. Error messages will be produced if it is invalid.
 *
 * @return 1 on success and 0 on failure.
 */
int EXPORT_CALL lou_compileString(const char *tableList, const char *inString);

/**
 * Get the typeform bit for the named emphasis class.
 *
 * If the table defines the specified emphasis class the corresponding
 * typeform is returned. Else the return value is 0.
 */
formtype EXPORT_CALL lou_getTypeformForEmphClass(const char *tableList, const char *emphClass);

/**
 * Set the path used for searching for tables and liblouisutdml files.
 *
 * Overrides the installation path. */
char *EXPORT_CALL lou_setDataPath(const char *path);

/**
 * Get the path set in the previous function. */
char *EXPORT_CALL lou_getDataPath();

typedef enum {
  LOG_ALL = 0,
  LOG_DEBUG = 10000,
  LOG_INFO = 20000,
  LOG_WARN = 30000,
  LOG_ERROR = 40000,
  LOG_FATAL = 50000,
  LOG_OFF = 60000
} logLevels;

typedef void (*logcallback)(logLevels level, const char *message);

/**
 * Register logging callbacks
 * Set to NULL for default callback.
 */
void EXPORT_CALL lou_registerLogCallback(logcallback callback);

/**
 * Set the level for logging callback to be called at
 */
void EXPORT_CALL lou_setLogLevel(logLevels level);

/* =========================  BETA API ========================= */

// Use the following two function with care, API is subject to change!

/**
 * Parse, analyze and index tables.
 *
 * This function must be called prior to lou_findTable(). An error
 * message is given when a table contains invalid or duplicate
 * metadata fields.
 */
void EXPORT_CALL lou_indexTables(const char **tables);

/**
 * Find the best match for a query.
 *
 * Returns a string with the table name. Returns NULL when no match
 * can be found. An error message is given when the query is invalid.
 */
char *EXPORT_CALL lou_findTable(const char *query);

/* ====================== END OF BETA API ====================== */

/**
 * Free all memory allocated by liblouis.
 *
 * This function should be called at the end of the application to
 * free all memory allocated by liblouis.
 */
void EXPORT_CALL lou_free();

#ifdef __cplusplus
}
#endif /* __cplusplus */
#endif /* __LIBLOUIS_H_ */
