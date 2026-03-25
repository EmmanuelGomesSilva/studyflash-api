package com.emmanuel.studyflash.studyflash.shared.handler;

import com.emmanuel.studyflash.studyflash.shared.dto.ErrorResponse;
import com.emmanuel.studyflash.studyflash.shared.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {


    private ResponseEntity<ErrorResponse> buildError(
            Exception ex,
            HttpStatus status,
            HttpServletRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubjectNotFound(
            SubjectNotFoundException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(InvalidOrderIndexException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOrderIndex(
            InvalidOrderIndexException ex,
            HttpServletRequest request) {
        return buildError(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTopicNotFound(
            TopicNotFoundException ex,
            HttpServletRequest request) {
        return buildError(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(FlashCardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFlashCardNotFound(
            FlashCardNotFoundException ex,
            HttpServletRequest request) {
        return buildError(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSessiondNotFound(
            SessionNotFoundException ex,
            HttpServletRequest request) {
        return buildError(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(StudyResultNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudyResultNotFound(
            StudyResultNotFoundException ex,
            HttpServletRequest request) {
        return buildError(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(FlashcardAlreadyAnsweredException.class)
    public ResponseEntity<ErrorResponse> handleFlashcardAlreadyAnswered(
            FlashcardAlreadyAnsweredException ex,
            HttpServletRequest request) {
        return buildError(ex, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ResultCannotBeNullException.class)
    public ResponseEntity<ErrorResponse> handleResultNull(
            ResultCannotBeNullException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ResultDoesNotBelongToSessionException.class)
    public ResponseEntity<ErrorResponse> handleResultInvalid(
            ResultDoesNotBelongToSessionException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(SessionNotActiveException.class)
    public ResponseEntity<ErrorResponse> handleSessionNotActive(
            SessionNotActiveException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(SessionCannotBeNullException.class)
    public ResponseEntity<ErrorResponse> handleSessionCannotBeNull(
            SessionCannotBeNullException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(FlashCardCannotBeNullException.class)
    public ResponseEntity<ErrorResponse> handleFlashCardCannotBeNull(
            FlashCardCannotBeNullException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(InvalidQuestionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidQuestion(
            InvalidQuestionException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(InvalidAnswerException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAnswer(
            InvalidAnswerException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(TopicRequiredException.class)
    public ResponseEntity<ErrorResponse> handleTopicRequired(
            TopicRequiredException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NameCannotBeNullException.class)
    public ResponseEntity<ErrorResponse> handleNameCannotBeNull(
            NameCannotBeNullException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(FlashCardInactiveException.class)
    public ResponseEntity<ErrorResponse> handleFlashCardInactive(
            FlashCardInactiveException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(DailyReviewLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleDailyReviewLimitExceeded(
            DailyReviewLimitExceededException ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.TOO_MANY_REQUESTS, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        return buildError(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


}
