import { MouseEventHandler } from 'react';

export interface PropTypes {
  children: string | JSX.Element;
  width?: number;
  height?: number;
  fontSize?: number;
  textColor?: string;
  backgroundColor?: string;
  isText?: boolean;
  clickHandler?: MouseEventHandler<HTMLButtonElement>;
  onClick?: MouseEventHandler<HTMLButtonElement>;
}
