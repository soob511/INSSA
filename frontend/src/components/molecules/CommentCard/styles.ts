import styled, { css } from 'styled-components';
import { StyledCommentCardTypes } from './types';

export const StyledCommentCard = styled.div<StyledCommentCardTypes>`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 1rem;
  border-radius: 1rem;

  ${({ isReComment, hasReComment }) =>
    (isReComment &&
      css`
        background-color: #e7e7e7;
      `) ||
    (!isReComment &&
      !hasReComment &&
      css`
        padding: 1rem 1rem 0 1rem;
      `)}

  ${({ isReComment, isWritingRecomment }) =>
    (isReComment &&
      css`
        background-color: #e7e7e7;
      `) ||
    (isWritingRecomment &&
      css`
        background-color: rgba(0, 0, 0, 0.2);
        border-radius: 0;
      `)}
`;

export const CommentHeader = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const IconButtonsWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 0.3rem;
`;
