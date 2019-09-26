import React from 'react';
import {Callout, Spinner} from '@blueprintjs/core';

type Props = {
    data: any | undefined
    error: any | undefined
    fetching: boolean
}
const QueryStateHandler: React.FC<Props> = ({data, error, fetching, children}) => {

    if (fetching) return <Spinner/>;

    if (error) return (
        <Callout intent='danger'>
            Hubo un error!
            Error: {JSON.stringify(error)}
        </Callout>
    );

    if (!data) return (
        <Callout intent='warning'>
            No se recibieron datos...
        </Callout>
    );

    return <>{children}</>;
};

export default QueryStateHandler;
