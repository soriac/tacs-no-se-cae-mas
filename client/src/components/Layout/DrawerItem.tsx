import React from 'react';
import styled from 'styled-components';
import {Card, Divider, Icon} from '@blueprintjs/core';
import {RouteComponentProps, withRouter} from 'react-router-dom';

const StyledCard = styled(Card)`
  margin-bottom: 0.5rem;
  
  display: flex;
  flex-direction: row;
  align-items: center;
  
  & > :first-child {
    margin-right: 1.5rem;
  }
  
  & > div {
    height: 20px;
  }
  
  & > p {
    margin: 0;
  }
`;

type Props = RouteComponentProps & {
    text: string;
    icon: string;
    href: string;
    handleClose: () => void;
}
const DrawerItem = withRouter(({text, icon, href, history, handleClose}: Props) => {
    const navigate = () => {
        history.push(href);
        handleClose();
    };

    return (
        <StyledCard interactive onClick={navigate}>
            <Icon icon={icon as any} iconSize={20}/>
            <Divider/>
            <p>{text}</p>
        </StyledCard>
    );

});

export default DrawerItem;