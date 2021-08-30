import React from 'react';
import {Text, StyleSheet, TouchableOpacity} from 'react-native';

export const Button = (props) => {
  const styles =
    props.type === 'action'
      ? actionStyles
      : props.disabled
      ? disabledStyles
      : normalStyles;

  return (
    <TouchableOpacity
      onPress={props.onPress}
      disabled={props.disabled}
      style={styles.container}>
      {typeof props.children === 'string' ? (
        <Text style={styles.text}>{props.children}</Text>
      ) : (
        props.children
      )}
    </TouchableOpacity>
  );
};

const baseStyles = StyleSheet.create({
  container: {
    borderRadius: 4,
    borderWidth: 2,
    paddingHorizontal: 15,
    paddingVertical: 5,
    marginLeft: 5,
    marginTop: 10,
    justifyContent: 'center',
  },
  text: {
    fontWeight: 'bold',
    fontSize: 18,
  },
});

const normalStyles = StyleSheet.create({
  container: {
    ...baseStyles.container,
    borderColor: '#6b5faf',
  },
  text: {
    ...baseStyles.text,
    color: '#6b5faf',
  },
});

const disabledStyles = StyleSheet.create({
  container: {
    ...baseStyles.container,
    borderColor: '#bababa',
  },
  text: {
    ...baseStyles.text,
    color: '#bababa',
  },
});

const actionStyles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    backgroundColor: '#fafafa',
  },
  text: {
    ...baseStyles.text,
    textDecorationLine: 'underline',
    color: '#6b5faf',
  },
});
