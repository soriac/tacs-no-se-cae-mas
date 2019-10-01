import React from 'react';
import {Spinner} from '@blueprintjs/core';
import UserComponent from '../../../components/User';
import {User} from '../../../api/types';
import {Root} from '../../../components/Root';
import {ContentContainer} from '../../../components/ContentContainer';

type Props = {
    user: User | undefined
    loading: boolean
}
const Layout: React.FC<Props> = ({user, loading}) => {

    return (
        <Root>
            <ContentContainer>
                <h2>Welcome!</h2>
                {
                    loading ?
                        <Spinner/>
                        : user &&
                      <UserComponent user={user}/>
                }
            </ContentContainer>
        </Root>
    )
};

export default Layout;