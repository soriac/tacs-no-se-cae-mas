import styled from 'styled-components';
import {Card} from '@blueprintjs/core';

export const ContentContainer = styled(Card)`
    & > * {
        margin-top: 0;
        margin-bottom: 1.5rem;
    }
    
    display: flex;
    flex-direction: column;
    align-items: center;
    
    overflow-y: auto;
`;
